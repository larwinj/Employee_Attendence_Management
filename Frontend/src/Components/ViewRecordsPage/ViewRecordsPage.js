
import React, { useState } from 'react';
import axios from 'axios';
import '../../Assests/ViewRecordsPage.css';

const ViewRecordsPage = () => {
    const [department, setDepartment] = useState('All');
    const [employeeName, setEmployeeName] = useState('All');
    const [date, setDate] = useState('');
    const [records, setRecords] = useState([]);

    const handleFetchRecords = async () => {
        setRecords([]);
        if (department === 'All' && employeeName === 'All') {
            try {
                const response = await axios.get('http://localhost:8080/api/attendancerecords/detailsByDate', {
                    params: { date }
                });
                setRecords(response.data);
            } catch (error) {
                console.error('Error fetching records', error);
            }
        }
        else if(department === 'All' && employeeName !== 'All'){
            try {
                const response = await axios.get('http://localhost:8080/api/attendancerecords/detailsByName', {
                    params: { employeeName }
                });
                setRecords(response.data);
            } catch (error) {
                console.error('Error fetching records', error);
            }
        }
        else if(department !== 'All' && employeeName ==='All' && date === ''){
            console.log(department);
            try {
                const response = await axios.get('http://localhost:8080/api/employees/by-department', {
                    params: { department }
                });
                setRecords(response.data);
                console.log(records);
            } catch (error) {
                console.error('Error fetching records', error);
            }
        }
    }

    const handleTodayRecords = async () => {
        const date = new Date().toISOString().split('T')[0]; // Get current date in 'yyyy-mm-dd' format
        setRecords([]);
        if (department === 'All' && employeeName === 'All') {
            try {
                const response = await axios.get('http://localhost:8080/api/attendancerecords/detailsByDate', {
                    params: { date }
                });
                setRecords(response.data);
                if(records.length === 0){
                    alert('Today Attendence cannot been taken');
                }
            } 
            catch (error) {
                console.error('Error fetching records', error);
            }
        }
        // if(records.length === 0){
        //     alert('Today Attendence cannot been taken');
        // }
    }
    

    return (
        <div className="view-records-container">
            <div className="form-container">
                <select 
                    value={department} 
                    onChange={e => setDepartment(e.target.value)} 
                    className="input-field"
                >
                    <option value="All">Select Department</option>
                    <option value="Engineering">Engineering</option>
                    <option value="Medical">Medical</option>
                    <option value="Teaching">Teaching</option>
                    <option value="Cleaning">Cleaning</option>
                    <option value="Supervising">Supervising</option>
                    
                </select>

                <input 
                    type="text" 
                    placeholder="Employee Name" 
                    value={employeeName} 
                    onChange={e => setEmployeeName(e.target.value)} 
                    className="input-field"
                />

                <input 
                    type="date" 
                    value={date} 
                    onChange={e => setDate(e.target.value)} 
                    className="input-field"
                />

                <button onClick={handleFetchRecords} className="get-records-btn">
                    Get Records
                </button>
                <button onClick={handleTodayRecords} className="get-records-btn">
                    Today Attendence
                </button>
            </div>

            {records.length > 0 && (
                <div className="records-table-container">
                    <table className="records-table">
                        <thead>
                            <tr>
                                <th>Employee Name</th>
                                <th>Employee ID</th>
                                <th>Department</th>
                                <th>Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
  {records.map(record => (
    <tr key={record.id} className={record.status === 'Present'? 'present' : record.status === 'Absent'? 'absent' : ''}>
      <td>{record.name}</td>
      <td>{record.id}</td>
      <td>{record.department}</td>
      <td>{record.attendanceDate}</td>
      <td>{record.status}</td>
    </tr>
  ))}
</tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default ViewRecordsPage;
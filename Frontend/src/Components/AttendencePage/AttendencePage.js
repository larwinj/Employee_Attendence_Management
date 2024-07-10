import React, { useState } from 'react';
import '../../Assests/AttendencePage.css';
import axios from 'axios';
// import Navbar from './navbar';


const AttendancePage = () => {
    const [Pdate, setDate] = useState('');
    const [l,setL]=useState([]);
    const [attendance, setAttendance] = useState([
        { name: 'John Doe', id: '11', department: 'HR', status: '' },
        { name: 'Jane Smith', id: '2', department: 'IT', status: '' },
        { name: 'David', id: '3', department: 'HR', status: '' },
        { name: 'Archer', id: '4', department: 'IT', status: '' },
        { name: 'Brook', id: '5', department: 'HR', status: '' },
        { name: 'Nickoles', id: '6', department: 'IT', status: '' },
        { name: 'Charles', id: '7', department: 'HR', status: '' },
        { name: 'Franklin', id: '8', department: 'IT', status: '' },
        { name: 'Harris', id: '9', department: 'HR', status: '' },
        { name: 'Bob Brown', id: '10', department: 'IT', status: '' },
    ]);

    const handleStatusChange = (index, status) => {
        const newAttendance = [...attendance];
        newAttendance[index].status = status;
        setAttendance(newAttendance);
    
        if (status === 'Present') {
            setL((prevL) => [...prevL, newAttendance[index]]);
        } else {
            setL((prevL) => prevL.filter((employee) => employee.id !== newAttendance[index].id));
        }
    };
    const handleSubmit = async () => {
        const currentTime = new Date().toLocaleTimeString('en-US', { hour12: false });
        // const date = new Date().toISOString().split('T')[0]; // Get current date in 'yyyy-mm-dd' format
    
        try {
            await axios.post(`http://localhost:8080/api/attendancerecords/addData/${Pdate}/${currentTime}`, l);
            alert('Attendance submitted successfully!');
        } catch (error) {
            console.error('Error submitting attendance', error);
        }
    };
    return (
        <div>
           <nav className="navbar111">
                <h1>Attendance Management</h1>
            </nav>
            <div className="date-input">
                <input 
                    type="date" 
                    value={Pdate} 
                    onChange={e => setDate(e.target.value)} 
                />
            </div>
        <div className="attendance-container">
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>ID</th>
                        <th>Department</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    {attendance.map((employee, index) => (
                        <tr key={employee.id} className={employee.status === 'Present' ? 'present-row' : employee.status === 'Absent' ? 'absent-row' : ''}>
                            <td>{employee.name}</td>
                            <td>{employee.id}</td>
                            <td>{employee.department}</td>
                            <td>
                                <button 
                                    className="present"
                                    onClick={() => handleStatusChange(index, 'Present')}
                                >
                                    Present
                                </button>
                                <button 
                                    className="absent"
                                    onClick={() => handleStatusChange(index, 'Absent')}
                                >
                                    Absent
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <button onClick={handleSubmit} className="submit-btn">Submit</button>
        </div>
        </div>
    );
};

export default AttendancePage;
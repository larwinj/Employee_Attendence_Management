import React, { useState } from 'react';
import axios from 'axios';
import '../../Assests/OvertimePage.css';

import 'react-toastify/dist/ReactToastify.css';

const OverTimePage = () => {
    const [leaveRequests, setLeaveRequests] = useState([]);

    const handleFetchLeaveRequests = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/overtimerecords/details');
            setLeaveRequests(response.data);
        } catch (error) {
            console.error('Error fetching records', error);
        }
    };

    return (
        <div className="leave-request-container">
            <nav className="navbar1">
                <h1>OverTimeRecord</h1>
            </nav>

            <div className="button-container">
                <button onClick={handleFetchLeaveRequests} className="fetch-leave-btn">
                    Show OverTime Records
                </button>
            </div>

            {leaveRequests.length > 0 && (
                <div className="leave-table-container">
                    <table className="leave-table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>ID</th>
                                <th>OverTime Date</th>
                                <th>OverTime Duration</th>
                                <th>Reason</th>
                            </tr>
                        </thead>
                        <tbody>
                            {leaveRequests.map(request => (
                                <tr key={request.employeeID}>
                                    <td>{request.empName}</td>
                                    <td>{request.employeeID}</td>
                                    <td>{request.overtimeDate}</td>
                                    <td>{request.overtimeHours}</td>
                                    <td>{request.reason}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default OverTimePage;
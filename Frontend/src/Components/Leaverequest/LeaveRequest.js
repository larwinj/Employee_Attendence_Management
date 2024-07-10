import React, { useState } from 'react';
import axios from 'axios';
import '../../Assests/LeaveRequestPage.css';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const LeaveRequestPage = () => {
    const [leaveRequests, setLeaveRequests] = useState([]);

    const handleFetchLeaveRequests = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/leaverequests/details');
            setLeaveRequests(response.data);
        } catch (error) {
            console.error('Error fetching records', error);
        }
    };

    const handleProceed = async (requestId) => {
        try {
            await axios.delete(`http://localhost:8080/api/leaverequests/${requestId}`);

            setLeaveRequests(prevRequests => prevRequests.filter(request => request.leaveID !== requestId));

            toast.success('Leave granted successfully!');
        } catch (error) {
            console.error('Error processing leave request', error);
            toast.error('Failed to process leave request');
        }
    };

    return (
        <div className="leave-request-container">
            <nav className="navbar1">
                <h1>Leave Request</h1>
            </nav>

            <div className="button-container">
                <button onClick={handleFetchLeaveRequests} className="fetch-leave-btn">
                    Show Leave Requests
                </button>
            </div>

            {leaveRequests.length > 0 && (
                <div className="leave-table-container">
                    <table className="leave-table">
                        <thead>
                            <tr>
                                <th>LeaveRequestId</th>
                                <th>Name</th>
                                <th>ID</th>
                                <th>Department</th>
                                <th>From</th>
                                <th>To</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {leaveRequests.map(request => (
                                <tr key={request.employeeID}>
                                    <td>{request.leaveID}</td>
                                    <td>{request.employeeName}</td>
                                    <td>{request.employeeID}</td>
                                    <td>{request.department}</td>
                                    <td>{request.startDate}</td>
                                    <td>{request.endDate}</td>
                                    <td>
                                        <button onClick={() => handleProceed(request.leaveID)} className="proceed-btn">
                                            Proceed
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            <ToastContainer />
        </div>
    );
};

export default LeaveRequestPage;
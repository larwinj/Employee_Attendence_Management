import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './Components/MainPage/MainPage';
import AttendancePage from './Components/AttendencePage/AttendencePage';
import ViewRecordsPage from './Components/ViewRecordsPage/ViewRecordsPage';
import LeaveRequestPage from './Components/Leaverequest/LeaveRequest';
import OverTimePage from './Components/OverTimeRecords/OvertimePage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/attendance" element={<AttendancePage />} />
                <Route path="/view-records" element={<ViewRecordsPage />} />
                <Route path="/leave-request" element={<LeaveRequestPage />} />
                <Route path="/overtime-record" element={<OverTimePage />} />
                {/* Add more routes as needed */}
            </Routes>
        </Router>
    );
}

export default App;

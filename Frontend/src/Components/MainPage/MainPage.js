import React from 'react';
import { Link } from 'react-router-dom';
import '../../Assests/MainPage.css';
// import Navbar from '../Navbar/Navbar';

const MainPage = () => (
  <div className="main">
    <nav className="navbar111">
                <h1>Attendance Management</h1>
    </nav>
    <div className="main-container">
      <Link to="/attendance" className="nav-item">Attendance</Link>
      <Link to="/view-records" className="nav-item">View Records</Link>
      <Link to="/leave-request" className="nav-item">Leave Request</Link>
      <Link to="/overtime-record" className="nav-item">Overtime Record</Link>
    </div>
  </div>
);

export default MainPage;
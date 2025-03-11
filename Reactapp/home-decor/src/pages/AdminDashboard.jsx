import React from "react";

function AdminDashboard() {
  return (
    <div className="container">
      <h2>Admin Dashboard</h2>
      <ul className="list-group">
        <li className="list-group-item">Unblock Users</li>
        <li className="list-group-item">Add Stock</li>
        <li className="list-group-item">Update Stock</li>
        <li className="list-group-item">View All Users</li>
        <li className="list-group-item">View All Items</li>
        <li className="list-group-item">Delete an Item</li>
      </ul>
    </div>
  );
}

export default AdminDashboard;

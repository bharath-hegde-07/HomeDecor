import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function UserDashboard() {
  const navigate = useNavigate();
  const [items, setItems] = useState([]);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    try {
      const response = await axios.get("http://localhost:8080/items");
      setItems(response.data);
    } catch (error) {
      console.error("Error fetching items", error);
    }
  };

  return (
    <div className="container mt-4">
      {/* Welcome Message - Centered */}
      <div className="text-center">
        <h2>Welcome, User!</h2>
      </div>

      {/* Logout Button - Right Aligned */}
      <div className="d-flex justify-content-end">
        <button className="btn btn-danger mb-3" onClick={() => navigate("/login")}>
          Logout
        </button>
      </div>

      {/* Items List */}
      <h3>Available Home Decor Items</h3>
      <div className="row">
        {items.length > 0 ? (
          items.map((item) => (
            <div key={item.id} className="col-md-4 mb-3">
              <div className="card p-3 shadow-sm">
                <img
                  src={item.imageUrl}  // Fetching image from backend
                  alt={item.name}
                  className="card-img-top"
                  style={{ height: "200px", objectFit: "cover" }}
                />
                <div className="card-body text-center">
                  <p>Price: ₹{item.price}</p>
                  <h4>{item.name}</h4>
                  <button className="btn btn-primary">Add to Cart</button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <p>No items available</p>
        )}
      </div>

      {/* View Cart Button - Right Aligned Below All Items */}
      <div className="d-flex justify-content-end mt-4">
        <button className="btn btn-success" onClick={() => navigate("/cart")}>
          View Cart
        </button>
      </div>
    </div>
  );
}

export default UserDashboard;

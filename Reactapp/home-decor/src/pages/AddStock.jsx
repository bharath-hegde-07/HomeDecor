import React from "react";

function AddStock() {
  return (
    <div className="container">
      <h2>Add Stock</h2>
      <p>Fill out the form to add new stock items to your inventory.</p>
      <form>
        <div className="mb-3">
          <label>Item Name</label>
          <input type="text" className="form-control" placeholder="Enter item name" required />
        </div>
        <div className="mb-3">
          <label>Description</label>
          <textarea className="form-control" placeholder="Enter description" required></textarea>
        </div>
        <div className="mb-3">
          <label>Price</label>
          <input type="number" className="form-control" placeholder="Enter price" required />
        </div>
        <button type="submit" className="btn btn-primary">Add Item</button>
      </form>
    </div>
  );
}

export default AddStock;

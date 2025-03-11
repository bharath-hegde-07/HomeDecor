import React from "react";

function Home() {
  return (
    <div className="text-center">
      <h1>Welcome to HomeDecor</h1>
      <p>Your one-stop shop for elegant home decoration items.</p>
      
      <div style={{ width: "100%", maxWidth: "800px", margin: "0 auto" }}>
        <img
          src="https://cdn.thecoolist.com/wp-content/uploads/2017/08/Start-Simple-Then-Accent-living-room-idea.jpg"
          alt="Home Decor"
          className="img-fluid mt-3"
          style={{ objectFit: "cover" }} 
        />
      </div>
    </div>
  );
}

export default Home;

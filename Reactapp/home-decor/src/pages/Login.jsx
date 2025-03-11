/*import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add login logic here
    console.log("Login:", email, password);
    // Navigate to home or dashboard based on role
    navigate("/");
  };

  return (
    <div className="container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>Email</label>
          <input 
            type="email"
            className="form-control"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required 
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input 
            type="password"
            className="form-control"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required 
          />
        </div>
        <button type="submit" className="btn btn-primary">Login</button>
      </form>
    </div>
  );
}

export default Login;
*/
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../api";  

function Login() {
  const navigate = useNavigate();
  const [username, setUsername] = useState(""); 
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Call login API
    const response = await loginUser(username, password);  

    if (response.message === "Login successful") {
      localStorage.setItem("userRole", response.role); 

      if (response.role === "ROLE_USER") {
        navigate("/user-dashboard");  
      } else if (response.role === "ROLE_ADMIN") {
        navigate("/admin-dashboard"); 
      }
    } else {
      setErrorMessage(response.message);
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>Username</label>  
          <input
            type="text"  
            className="form-control"
            value={username}  
            onChange={(e) => setUsername(e.target.value)}  
            required
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">Login</button>
      </form>
    </div>
  );
}

export default Login;

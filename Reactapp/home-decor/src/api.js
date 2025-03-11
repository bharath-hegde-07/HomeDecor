// api.js
import axios from 'axios';

const apiUrl = 'http://localhost:8080';  

export const loginUser = async (username, password) => {
  try {
    const response = await axios.post(`${apiUrl}/user/login`, { username, password });
    return response.data;
  } catch (error) {
    console.error('Login error:', error);
    return { message: 'Login failed. Please check your credentials.' };
  }
};

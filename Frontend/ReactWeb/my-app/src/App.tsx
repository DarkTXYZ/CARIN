import React, { useEffect, useState } from 'react'
import axios from 'axios'
import './App.css'
import { type } from 'os'

type Post = {
  id: number,
  name: string
}

const App = () => {

  const [posts, setPosts] = useState<Post[]>([])

  const getPosts = async () => {
    try {
      const userPosts = await axios.get("http://localhost:8080/employees")
      setPosts(userPosts.data._embedded.employeeList);
    } catch (err: any) {
      console.error(err.message);
    }
  };

  useEffect(() => {
    getPosts()
    const interval = setInterval(() => {
      getPosts()
    }, 1000)
    return () => clearInterval(interval)
  }, [])  // includes empty dependency array

  return (
    <div>
      <h1>useEffect</h1>
      <ul>
        {posts.map((post: Post) => (
          <li key={post.id}>{post.name}</li>
        ))}
      </ul>
    </div>
  );
  
}
export default App;
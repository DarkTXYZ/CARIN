import React, { useEffect, useState } from 'react'
import axios from 'axios'
import './App.css'
import { type } from 'os'

type Post = {
  id: number,
  name: string,
  nums: Array<number>
  mapped : any
}

const App = () => {

  const [posts, setPosts] = useState([])
  
  const getPosts = async () => {
    try {
      const userPosts = await axios.get("http://localhost:8080/employees")
      .then(response => {
        setPosts(response.data);
        console.log(response);
      })
      // await axios.post(link, {
      //   name: 'Fred Flintstone',
      //   role: 'pilot',
      //   nums: [1,2,3,4]
      // })

    } catch (err: any) {
      console.error(err.message);
    }
  };

  useEffect(() => {
    getPosts()
    const interval = setInterval(() => {
      getPosts()
    }, 5000)
    return () => clearInterval(interval)
  }, [])  // includes empty dependency array

  return (
    <div>
      <h1>useEffect</h1>
      <ul>
        {posts.map((post: Post) => (
          <li key={post.id}>{post.name} {post.nums} {post.mapped.sss} {post.mapped.abc}</li>
        ))}
      </ul>
    </div>
  );
  
}
export default App;
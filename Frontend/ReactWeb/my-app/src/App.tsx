import React, { useEffect, useState } from 'react'
import axios from 'axios'
import './App.css'
import { type } from 'os'

type Post = {
  id: number,
  name: string,
  nums: Array<number>
  mapped: any,
  role: string
}

const App = () => {

  const [posts, setPosts] = useState<Post[]>([])

  const getPosts = async () => {
    try {

      await axios({
        method : 'put',
        url: 'http://localhost:8080/employees/1',
        data: {
          name: "Fredfffff" ,
          nums: [1,3,3] ,
          role: "gardener",
          mapped: {
            "ggg" : 1222,
            "lll" : 345
          }
        }
      })

      await axios({
        method: 'get',
        url: 'http://localhost:8080/employees',
        responseType: 'stream'
      }).then(response => {
        setPosts(response.data);
        console.log(response);
      })


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
  })  // includes empty dependency array

  return (
    <div>
      <h1>useEffect</h1>
      <ul>
        {posts.map((post: Post) => (
          <li key={post.id}>{post.name} {post.nums} {post.role} </li>
        ))}
      </ul>
    </div>
  );

}
export default App;
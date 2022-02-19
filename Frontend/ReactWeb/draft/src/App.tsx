import React, { useEffect } from 'react';
import './App.css';
import axios from 'axios';
import GameData from './GameData';
import Controller from './Controller';
import Field from './Field';
import { TransformWrapper, TransformComponent } from "react-zoom-pan-pinch";
import logo from './lib/atbd1.png'
class App extends React.Component {

	interval : any;

	state = {
		buy : false,
		time: 0,
		state: 0,
		pos: [],
		order: []
	}

	componentDidMount() {
		this.fetch();
	}

	componentDidUpdate() {
		this.fetch();
	}

	componentWillUnmount() {

	}

	fetch = () => {
		Controller.getData().then(resp => {
			this.setState(resp.data)
		})
	}

	fetchButton() {
		return (
			<div>
				<button className="text-3xl bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500" onClick={this.fetch}>Fetch</button>
			</div>
		)
	}

	render(): React.ReactNode {
		return (
			<div className='flex flex-col space-y-3'>
				<div className='flex justify-center'>
					{this.fetchButton()}
				</div>
				<Field name = "Jack" m = {30} n = {30} position = {this.state.pos} />
			</div>
		)
	}

	// render() {
	// 	return (
	// 	  <TransformWrapper>
	// 		<TransformComponent>
	// 		  <img src={logo} alt="test" />
	// 		</TransformComponent>
	// 	  </TransformWrapper>
	// 	);
	//   }
}

export default App;

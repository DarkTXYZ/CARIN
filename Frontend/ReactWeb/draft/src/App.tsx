import React, { useEffect } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import GameData from './GameData';
import Controller from './Controller';

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

	render(): React.ReactNode {
		return (
			<div>
				<div>
					<p>Hello word</p>
				</div>
				<div>
					<p>{this.state.time} {this.state.state} {this.state.pos} {this.state.order}</p>
				</div>
				<button onClick={this.fetch}></button>
			</div>
		)
	}
}

export default App;

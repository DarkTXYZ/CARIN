import React from 'react';
import './App.css';
import Controller from './Controller';
import Field from './Field';
import Shop from './Shop';
import logo from './lib/logo.png'
import Objective from './Objective';
import Tutorial from './Tutorial';
import WinScene from './WinScene';
import ReactAudioPlayer from 'react-audio-player';
import ReactSoundcloud from 'react-soundcloud-embedded';
import GameOverScene from './GameOverScene';

class App extends React.Component {

	interval: any;

	state = {
		m: 0,
		n: 0,
		state: 0,		// state of game
		shopState: [false, false, false],
		currency: 0,
		cost: [],
		posX: [], // position x of hosts
		posY: [], // position y of hosts
		hp: [],
		hpMax: [],
		type: [], // type of hosts
		objective: 0, // number of viruses left
		objectiveMax: 0,
		sound: require('./lib/blue.mp3')
	}

	componentDidMount() {
		this.fetch();
	}

	componentDidUpdate() {
		this.fetch();
	}

	fetch = () => {
		Controller.getData().then(resp => {
			this.setState(resp.data)
		})
	}

	render(): React.ReactNode {
		return (
			<div className='fixed select-none w-full h-full bg-gradient-to-l from-sky-600 to-red-600 bg-BG'>
				<ReactAudioPlayer className='fixed'
					src={this.state.sound}
					autoPlay = {true}
					controls={true} 
					loop={true}
					volume={0.4}
				/>

				<div className="flex justify-center">
					<div className='flex flex-col space-y-3'>
						<div className='flex justify-center my-5'>
							<img src={logo} style={{ height: 100 }} />
						</div>
						{this.state.state !== 0 &&
							<div className='flex flex-row justify-center space-x-10'>
								<div className='flex flex-col space-y-4 h-full'>
									<Objective objective={this.state.objective} objectiveMax={this.state.objectiveMax} />
									{/* <div className='w-40'>
										<ReactSoundcloud hideRelated={true} autoPlay={true} height='643px' url="https://soundcloud.com/zz_music_jpn/yoitoyakoun-zaza-flip" />
									</div> */}
								</div>
								<div className='flex flex-col'>
									<Field m={this.state.n} n={this.state.m} px={this.state.posX} py={this.state.posY} t={this.state.type} hp={this.state.hp} hpMax={this.state.hpMax} />
								</div>
								<div>
									<Shop canBuy={this.state.shopState} cost={this.state.cost} currency={this.state.currency} />
								</div>
							</div>
						}
						{this.state.state === 0 &&
							<Tutorial />
						}
						{this.state.state === 2 &&
							<WinScene />
						}
						{this.state.state === 3 &&
							<GameOverScene />
						}
					</div>
				</div>

			</div>

		)
	}
}

export default App;

import { useState } from 'react'
import Controller from './Controller'
import ShopTile from './ShopTile'

function Shop(props: any) {

    const [placeState, setPlaceState] = useState<number>(0)
    const [job, setJob] = useState<any>('')

    Controller.getInput("placestate").then(resp => {
        setPlaceState(resp)
    })
    Controller.getInput("job").then(resp => {
        setJob(resp)
    })

    let canBuy: number[] = props.canBuy

    const selected = (typeATBD: string) => {
        Controller.sendInput("job", {
            job: typeATBD
        })
        Controller.sendInput("placestate", {
            placeState: 1
        })
        Controller.sendInput("selected", {
            selectedX: -1,
            selectedY: -1,
        })
    }

    const cancel = () => {
        Controller.sendInput("placestate", {
            placeState: 0
        })
        Controller.sendInput("selected", {
            selectedX: -1,
            selectedY: -1,
        })
    }

    let cost = props.cost
    let name = ['Mercy', 'Ana', 'Lucio']
    let shopTile = []
    for (let i = 0; i < 3; ++i) {
        if (canBuy[i])
            shopTile.push(<ShopTile onClick={() => {
                selected('atbd' + (i + 1))
                Controller.sendInput('movestate', {
                    moveState: 0
                })
            }} canBuy={canBuy[i]} type={i + 1} cost={cost[i]} selected={job} name={name[i]} placeState={placeState} />)
        else
            shopTile.push(<ShopTile canBuy={canBuy[i]} type={i + 1} cost={cost[i]} selected={job} name={name[i]} placeState={placeState} />)
    }

    return (

        <div className='flex flex-col space-y-2'>

            <div className='select-none flex justify-center bg-gradient-to-b from-yellow-500 to-yellow-300 w-40 p-2 font-bold text-lg rounded-xl'>
                Currency : {props.currency}
            </div>
            <div className='flex flex-col space-y-2 justify-center'>
                {shopTile[0]}
                {shopTile[1]}
                {shopTile[2]}
            </div>

            {placeState !== 0 &&
                <div className='flex flex-row justify-between space-x-2 w-40'>
                    <div className='hover:drop-shadow-xl'>
                        <button className='text-xl font-bold bg-gradient-to-l from-green-600 to-green-400 p-2 border-2 border-green-300 rounded-md hover:scale-105 duration-300 ease-out'>Place</button>
                    </div>
                    <div className='hover:drop-shadow-xl'>
                        <button className='text-xl font-bold bg-gradient-to-l from-red-600 to-red-400 p-2 border-2 border-red-300 rounded-md hover:scale-105 duration-300 ease-out'
                            onClick={cancel}>Cancel</button>
                    </div>
                </div>
            }


        </div>

    )
}

export default Shop

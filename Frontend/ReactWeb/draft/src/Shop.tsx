import axios from 'axios'
import { useState } from 'react'
import Controller from './Controller'
import tile1 from './lib/atbdTile1.png'
import tile2 from './lib/atbdTile2.png'
import tile3 from './lib/atbdTile3.png'
import ShopTile from './ShopTile'

function Shop(props: any) {

    const [clickState, setClickState] = useState<number>(0)
    const [clickType, setClickType] = useState<string>('')
    const [hover, setHover] = useState<boolean>(false)
    const [hover1, setHover1] = useState<boolean>(false)
    const [hover2, setHover2] = useState<boolean>(false)

    const toggleHover = () => {
        setHover(!hover)
    }
    const toggleHover1 = () => {
        setHover1(!hover1)
    }
    const toggleHover2 = () => {
        setHover2(!hover2)
    }

    let canBuy: number[] = props.canBuy

    const selected = (typeATBD: string) => {
        setClickState(1)
        setClickType(typeATBD)
        Controller.sendPos({
            posX_placement: -1,
            posY_placement: -1,
        })
    }

    const cancel = () => {
        setClickState(0)
        setClickType('')
        Controller.sendPos({
            posX_placement: -1,
            posY_placement: -1,
        })
    }

    Controller.sendClickState({
        clickState: clickState
    })
    Controller.sendClickType({
        clickType: clickType
    })

    // Controller.getPosX().then(resp => setPosX(resp))
    // Controller.getPosY().then(resp => setPosY(resp))

    let cost = props.cost
    let name = ['Mercy','Ana','Lucio']
    let shopTile = []
    for(let i = 0 ; i < 3 ; ++i) {
        if(canBuy[i])
            shopTile.push(<ShopTile onClick={() => selected('atbd' + (i+1))} canBuy={canBuy[i]} type={i+1} cost={cost[i]} selected={clickType} name={name[i]} />)
        else
            shopTile.push(<ShopTile canBuy={canBuy[i]} type={i+1} cost={cost[i]} selected={clickType} name={name[i]} />) 
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

            <div className='flex flex-row justify-between space-x-2 w-40'>
                <div className='hover:drop-shadow-xl'>
                    {clickState !== 0 && <button className='bg-gradient-to-l from-green-600 to-green-400 p-2 rounded-xl text-xl font-bold hover:scale-105 duration-300 ease-out'>Place</button>}
                </div>
                <div className='hover:drop-shadow-xl'>
                    {clickState !== 0 && <button className='bg-gradient-to-l from-red-600 to-red-400 p-2 rounded-xl text-xl font-bold hover:scale-105 duration-300 ease-out '
                        onClick={cancel}>Cancel</button>}
                </div>
            </div>

        </div>

    )
}

export default Shop

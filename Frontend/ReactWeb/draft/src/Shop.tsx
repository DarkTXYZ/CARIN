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
    let tiles = []

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

    if (canBuy[0] === 0)
        tiles.push(<img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover} className="opacity-50 border-2 rounded-2xl" />)
    else if (clickType === 'atbd1')
        tiles.push(<img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover} className="opacity-100 rounded-2xl border-4 border-green-400" onClick={() => selected('atbd1')} />)
    else
        tiles.push(<img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover} className="opacity-100 rounded-2xl border-2 hover:border-yellow-400 hover:border-4" onClick={() => selected('atbd1')} />)

    if (canBuy[1] === 0)
        tiles.push(<img src={tile2} onMouseEnter={toggleHover1} onMouseLeave={toggleHover1} className="opacity-50 border-2 rounded-2xl" />)
    else if (clickType === 'atbd2')
        tiles.push(<img src={tile2} onMouseEnter={toggleHover1} onMouseLeave={toggleHover1} className="opacity-100 rounded-2xl border-4 border-green-400" onClick={() => selected('atbd2')} />)
    else
        tiles.push(<img src={tile2} onMouseEnter={toggleHover1} onMouseLeave={toggleHover1} className="opacity-100 rounded-2xl border-2 hover:border-yellow-400 hover:border-4" onClick={() => selected('atbd2')} />)

    if (canBuy[2] === 0)
        tiles.push(<img src={tile3} onMouseEnter={toggleHover2} onMouseLeave={toggleHover2} className="opacity-50 border-2 rounded-2xl" />)
    else if (clickType === 'atbd3')
        tiles.push(<img src={tile3} onMouseEnter={toggleHover2} onMouseLeave={toggleHover2} className="opacity-100 rounded-2xl border-4 border-green-400" onClick={() => selected('atbd3')} />)
    else
        tiles.push(<img src={tile3} onMouseEnter={toggleHover2} onMouseLeave={toggleHover2} className="opacity-100 rounded-2xl border-2 hover:border-yellow-400 hover:border-4" onClick={() => selected('atbd3')} />)

    let cost = props.cost

    return (

        <div className='flex flex-col space-y-2'>

            <ShopTile onClick={() => selected('atbd1')} canBuy={canBuy[0]} type={1} cost={cost[0]} selected={clickType} name="Mercy" />
            <ShopTile onClick={() => selected('atbd2')} canBuy={canBuy[1]} type={2} cost={cost[1]} selected={clickType} name="Ana" />
            <ShopTile onClick={() => selected('atbd3')} canBuy={canBuy[2]} type={3} cost={cost[2]} selected={clickType} name="Lucio" />

            <div className='flex flex-row space-x-3 translate-y-10'>
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

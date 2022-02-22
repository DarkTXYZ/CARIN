import axios from 'axios'
import { useState } from 'react'
import Controller from './Controller'
import tile1 from './lib/atbdTile1.png'
import tile2 from './lib/atbdTile2.png'
import tile3 from './lib/atbdTile3.png'

function Shop(props: any) {

    const [clickState, setClickState] = useState<number>(0)
    const [clickType, setClickType] = useState<string>('')
    const [hover, setHover] = useState<boolean>(false)
    const [hover1, setHover1] = useState<boolean>(false)
    const [hover2, setHover2] = useState<boolean>(false)
    const [posX, setPosX] = useState<number>(-1)
    const [posY, setPosY] = useState<number>(-1)

    const toggleHover = () => {
        setHover(!hover)
    }
    const toggleHover1 = () => {
        setHover1(!hover1)
    }
    const toggleHover2 = () => {
        setHover2(!hover2)
    }

    let isBuy: number[] = props.isBuy
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
    }

    Controller.sendState({
        state: clickState
    })
    Controller.sendType({
        type: clickType
    })

    Controller.getPosX().then(resp => setPosX(resp))
    Controller.getPosY().then(resp => setPosY(resp))

    if (isBuy[0] === 0)
        tiles.push(<img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover} className="opacity-50 border-2 rounded-2xl" />)
    else
        tiles.push(<img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover} className="opacity-100 rounded-2xl border-2 hover:border-yellow-400 hover:border-4" onClick={() => selected('atbd1')} />)

    if (isBuy[1] === 0)
        tiles.push(<img src={tile2} onMouseEnter={toggleHover1} onMouseLeave={toggleHover1} className="opacity-50 border-2 rounded-2xl" />)
    else
        tiles.push(<img src={tile2} onMouseEnter={toggleHover1} onMouseLeave={toggleHover1} className="opacity-100 rounded-2xl border-2 hover:border-yellow-400 hover:border-4" onClick={() => selected('atbd2')} />)

    if (isBuy[2] === 0)
        tiles.push(<img src={tile3} onMouseEnter={toggleHover2} onMouseLeave={toggleHover2} className="opacity-50 border-2 rounded-2xl" />)
    else
        tiles.push(<img src={tile3} onMouseEnter={toggleHover2} onMouseLeave={toggleHover2} className="opacity-100  rounded-2xl border-2 hover:border-yellow-400 hover:border-4" onClick={() => selected('atbd3')} />)

    return (
        <div>
            <div className='flex flex-col space-y-2 w-40 content-between'>
                
                <div>
                    <div className='flex flex-row items-center space-x-3 hover:drop-shadow-xl duration-300 ease-out hover:scale-105'>
                        {tiles[0]}
                        {/* <div className="opacity-0 hover:opacity-100 duration-300 absolute inset-0 z-10 flex justify-center items-center text-4xl text-gray-600 font-semibold translate-x-60"> */}
                        <div>{hover && <p className='text-4xl text-gray-600 font-semibold outline-8'>Mercy</p>}</div>
                    </div>
                    <div className='flex flex-row items-center space-x-3 hover:drop-shadow-xl duration-300 ease-out hover:scale-105'>
                        {tiles[1]}
                        <div>{hover1 && <p className='text-4xl text-gray-600 font-semibold outline-8'>Ana</p>}</div>
                    </div>
                    <div className='flex flex-row items-center space-x-3 hover:drop-shadow-xl duration-300 ease-out hover:scale-105'>
                        {tiles[2]}
                        <div>{hover2 && <p className='text-4xl text-gray-600 font-semibold outline-8'>Lucio</p>}</div>
                    </div>
                </div>

                <div className='flex flex-row space-x-3'>
                    <div className='hover:drop-shadow-xl'>
                        {clickState !== 0 && <button className='bg-gradient-to-l from-green-600 to-green-400 p-2 rounded-xl text-xl font-bold hover:scale-105 duration-300 ease-out'>Place</button>}
                    </div>
                    <div className='hover:drop-shadow-xl'>
                        {clickState !== 0 && <button className='bg-gradient-to-l from-red-600 to-red-400 p-2 rounded-xl text-xl font-bold hover:scale-105 duration-300 ease-out '
                            onClick={cancel}>Cancel</button>}
                    </div>
                </div>

            </div>
        </div>
    )
}

export default Shop
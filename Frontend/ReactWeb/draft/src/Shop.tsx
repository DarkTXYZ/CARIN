import { useState } from 'react'
import Controller from './Controller'
import tile1 from './lib/atbdTile1.png'
import tile2 from './lib/atbdTile2.png'
import tile3 from './lib/atbdTile3.png'

function Shop(props: any) {

    const [state , setState] = useState<number>(0)
    const [hover , setHover] = useState<boolean>(false)
    const [hover1 , setHover1] = useState<boolean>(false)
    const [hover2 , setHover2] = useState<boolean>(false)

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

    if (isBuy[0] === 0)
        tiles.push(<img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover} className="opacity-50 border-4 rounded-2xl" />)
    else
        tiles.push(<img src={tile1} onMouseEnter={toggleHover} onMouseLeave={toggleHover} className="opacity-100 border-4 rounded-2xl" onMouseDown={() => {
            setState(1)
            Controller.sendType({
                type: 'atbd1'
            })
            Controller.sendState({
                state: state
            })
        }} />)

    if (isBuy[1] === 0)
        tiles.push(<img src={tile2} onMouseEnter={toggleHover1} onMouseLeave={toggleHover1} className="opacity-50 border-4 rounded-2xl" />)
    else
        tiles.push(<img src={tile2} onMouseEnter={toggleHover1} onMouseLeave={toggleHover1} className="opacity-100 border-4 rounded-2xl" onMouseDown={() => {
            setState(1)
            Controller.sendType({
                type: 'atbd2'
            })
            Controller.sendState({
                state: state
            })
        }} />)

    if (isBuy[2] === 0)
        tiles.push(<img src={tile3} onMouseEnter={toggleHover2} onMouseLeave={toggleHover2} className="opacity-50 border-4 rounded-2xl" />)
    else
        tiles.push(<img src={tile3} onMouseEnter={toggleHover2} onMouseLeave={toggleHover2} className="opacity-100 border-4 rounded-2xl" onMouseDown={() => {
            setState(1)
            Controller.sendType({
                type: 'atbd3'
            })
            Controller.sendState({
                state: state
            })
        }} />)

    return (
        <div>
            <div className='flex flex-col space-y-2 w-40'>
                <div className='flex flex-row items-center space-x-3'>
                    {tiles[0]}
                    {/* <div className="opacity-0 hover:opacity-100 duration-300 absolute inset-0 z-10 flex justify-center items-center text-4xl text-gray-600 font-semibold translate-x-60"> */}
                    <div>{hover && <p className='text-4xl text-gray-600 font-semibold outline-8'>Mercy</p>}</div>
                </div>
                <div className='flex flex-row items-center space-x-3'>
                    {tiles[1]}
                    <div>{hover1 && <p className='text-4xl text-gray-600 font-semibold outline-8'>Ana</p>}</div>
                </div>
                <div className='flex flex-row items-center space-x-3'>
                    {tiles[2]}
                    <div>{hover2 && <p className='text-4xl text-gray-600 font-semibold outline-8'>Lucio</p>}</div>
                </div>
            </div>
        </div>
    )
}

export default Shop
import Controller from "./Controller"

function WinScene() {

    const clicked = () => {
        Controller.sendInput('placestate', {
            placeState : 999
        })
    }
    
    return (
        <div>
            <div className="fixed left-0 bottom-0 opacity-60 w-full h-full bg-gray-400">

            </div>
            <div className=" fixed -translate-x-1/2 bottom-1/2 left-1/2 flex flex-col items-center">
                <div className="flex flex-col items-center">
                    <div className = "p-3 bg-white -skew-y-3 shadow-xl hover:scale-105 duration-300 ease-out">
                        <div className="animate-fade text-9xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-yellow-200 to-yellow-500 ">
                            CONGRATULATIONS
                        </div>
                    </div>

                    <div className="mt-3 text-3xl font-semibold -skew-y-3 bg-yellow-400 p-2 shadow-xl hover:scale-105 duration-300 ease-out">
                        You win the game!
                    </div>
                </div>

                <div>
                    <button className="animate-pulse mt-10 p-2 rounded-xl text-3xl font-semibold bg-gradient-to-r from-green-600 to-emerald-300 hover:scale-105 duration-300 ease-out hover:drop-shadow-xl" onClick={clicked}>
                        Restart
                    </button>
                </div>



            </div>
        </div>

    )
}

export default WinScene
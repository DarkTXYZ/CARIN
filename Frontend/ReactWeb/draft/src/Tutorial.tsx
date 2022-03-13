import Controller from "./Controller"
import howtoplay from "./lib/howtoplay.png"

function Tutorial(props : any) {

    const clicked = () => {
        Controller.sendInput('placestate', {
            placeState: 69
        })
    }

    return (
        <div className="p-8 select-none bg-gradient-to-r from-gray-100 to-gray-300 w-3/4 to-blue-500 flex flex-col space-y-2 items-center self-center rounded-xl p-2 text-2xl">
            <div className="p-2 hover:scale-105 duration-300 ease-out">
                <img src={howtoplay} className="rounded-xl" />
            </div>
            <div onClick={clicked} className="relative inline-flex items-center px-6 py-2 overflow-hidden text-white bg-green-500 rounded-xl group active:bg-green-700 focus:outline-none focus:ring hover:scale-105">
                <span className="absolute left-0 transition-transform -translate-x-full group-hover:translate-x-3">
                    <svg className="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="black">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8l4 4m0 0l-4 4m4-4H3" />
                    </svg>
                </span>

                <span className="text-xl font-bold text-black transition-all group-hover:ml-4">
                    Start
                </span>
            </div>
        </div>
    )
}

export default Tutorial
function Objective(props: any) {
    return (<div className="select-none flex flex-col space-y-2 text-lg rounded-xl p-2 bg-gradient-to-r from-sky-400 to-blue-500 items-center font-bold w-40 hover:scale-110 duration-300 ease-out">
        <div>Remaining : {props.objectiveMax - props.objective}</div>
        {/* <div>Virus: {props.objectiveMax}</div> */}
    </div>)
}

export default Objective
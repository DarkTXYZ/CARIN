function Objective(props: any) {
    return (<div className="flex flex-col space-y-2 text-lg rounded-xl p-2 bg-gradient-to-b from-cyan-500 to-red-700 text-gray-100 items-center font-bold w-40">
        <div>Remaining : {props.objectiveMax - props.objective}</div>
        {/* <div>Virus: {props.objectiveMax}</div> */}
    </div>)
}

export default Objective
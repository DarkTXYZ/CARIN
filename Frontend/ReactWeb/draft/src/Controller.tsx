import axios from "axios"

class Controller {
    
    // Gamedata Controller
    static async getData() {
        const data = await axios.get("http://localhost:8080/gamedata")
        return data
    }

    // Input Controller
    static async getAllInput() {
        const resp = await axios.get<number>('http://localhost:8080/input')
        return resp.data
    }

    static async sendAllInput(data: any) {
        await axios.put('http://localhost:8080/input/put', data)
    }

    static async getInput(fetchKey : any) {
        const resp = await axios.get<number>('http://localhost:8080/input/'+fetchKey)
        return resp.data
    }

    static async sendInput(sendKey : any, data: any) {
        await axios.put('http://localhost:8080/input/put/' + sendKey, data)
    }

}

export default Controller
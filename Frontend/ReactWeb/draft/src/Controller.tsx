import axios from "axios"

class Controller {
    static async getData() {
        const data = await axios.get("http://localhost:8080/gamedata")
        return data
    }

    static async sendPos(data: any) {
        await axios.put('http://localhost:8080/input/put/pos', data)
    }

    static async sendType(data: any) {
        await axios.put('http://localhost:8080/input/put/type', data)
    }

    // Web State
    static async sendState(data: any) {
        await axios.put('http://localhost:8080/input/put/state', data)
    }

    static async getState() {
        const resp = await axios.get<number>('http://localhost:8080/input/state')
        return resp.data
    }

    static async getPosX() {
        const resp = await axios.get('http://localhost:8080/input/')
        return resp.data.posX_placement
    }

    static async getPosY() {
        const resp = await axios.get('http://localhost:8080/input/')
        return resp.data.posY_placement
    }
}

export default Controller
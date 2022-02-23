import axios from "axios"

class Controller {
    static async getData() {
        const data = await axios.get("http://localhost:8080/gamedata")
        return data
    }

    static async sendPos(data: any) {
        await axios.put('http://localhost:8080/input/put/pos', data)
    }

    static async sendClickType(data: any) {
        await axios.put('http://localhost:8080/input/put/clicktype', data)
    }

    // Web State
    static async sendClickState(data: any) {
        await axios.put('http://localhost:8080/input/put/clickstate', data)
    }

    static async getClickState() {
        const resp = await axios.get<number>('http://localhost:8080/input/clickstate')
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
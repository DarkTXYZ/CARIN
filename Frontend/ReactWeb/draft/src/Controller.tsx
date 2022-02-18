import axios from "axios"

class Controller {
    static async getData() {
        const data = await axios.get("http://localhost:8080/gamedata")
        return data
    }
}

export default Controller
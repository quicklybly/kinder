import axios from "axios";
import urlConstants from "@/urlConstants";

export async function getImage(id) {
    let tmpImage
    await axios.get(urlConstants.imageBaseURL + "/" + id, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
        responseType: 'blob',
    }).then((resp) => {
        tmpImage = resp.data;
        return tmpImage
    }).catch((e) => {
        console.log(e)
    })
    return tmpImage
}
// Este host se usara para conectarse desde otro dispositivo en la red, en el caso
// de macOS o GNU/Linux seria el hostname y para windows la IP de tu maquina
const localhost = "editar-este-valor"

const express = require("express")
const cors = require("cors")

const app = express()

// Servir contenido estatico
app.use(express.static('public'))
// Habilitar CORS por defecto
app.use(cors())
// Habilitar request POST con formato JSON
app.use(express.json())

const jugadores = []

class Jugador {
    constructor(id) {
        this.id = id
    }

    asignarMokepon(mokepon) {
        this.mokepon = mokepon
    }

    actualizarPosicion(x, y) {
        this.x = x
        this.y = y
    }

    asignarAtaques(ataques) {
        this.ataques = ataques
    }
}

class Mokepon {
    constructor(nombre) {
        this.nombre = nombre
    }
}

app.get("/unirse", (req, res) => {
    // Manejar el ID como campo de texto (string)
    const id = `${Math.random()}`

    const jugador = new Jugador(id)

    jugadores.push(jugador)

    res.setHeader("Access-Control-Allow-Origin", "*")

    res.send(id)
})

app.post("/mokepon/:jugadorId", (req, res) => {
    const jugadorId = req.params.jugadorId || ""
    const nombre = req.body.mokepon || ""
    const mokepon = new Mokepon(nombre)
    
    const jugadorIndex = jugadores.findIndex((jugador) => jugadorId == jugador.id)

    if (jugadorIndex >= 0) {
        jugadores[jugadorIndex].asignarMokepon(mokepon)
    }

    console.log(jugadores)
    console.log(jugadorId)
    res.end()
})

app.post("/mokepon/:jugadorId/posicion", (req, res) => {
    const jugadorId = req.params.jugadorId || ""
    const x = req.body.x || 0
    const y = req.body.y || 0

    const jugadorIndex = jugadores.findIndex((jugador) => jugadorId == jugador.id)

    if (jugadorIndex >= 0) {
        jugadores[jugadorIndex].actualizarPosicion(x, y)
    }

    const enemigos = jugadores.filter((jugador) => jugadorId != jugador.id)

    res.send({
        enemigos
    })
})

app.post("/mokepon/:jugadorId/ataques", (req, res) => {
    const jugadorId = req.params.jugadorId || ""
    const ataques = req.body.ataques || []
    
    const jugadorIndex = jugadores.findIndex((jugador) => jugadorId == jugador.id)

    if (jugadorIndex >= 0) {
        jugadores[jugadorIndex].asignarAtaques(ataques)
    }

    res.end()
})

app.get("/mokepon/:jugadorId/ataques", (req, res) => {
    const jugadorId = req.params.jugadorId || ""
    const jugador = jugadores.find((jugador) => jugador.id == jugadorId)

    res.send({
        ataques: jugador.ataques || []
    })
})

app.listen(8080, () => {
    console.log("")
    console.log("███    ███  ██████  ██   ██ ███████ ██████   ██████  ███    ██ ")
    console.log("████  ████ ██    ██ ██  ██  ██      ██   ██ ██    ██ ████   ██ ")
    console.log("██ ████ ██ ██    ██ █████   █████   ██████  ██    ██ ██ ██  ██ ")
    console.log("██  ██  ██ ██    ██ ██  ██  ██      ██      ██    ██ ██  ██ ██ ")
    console.log("██      ██  ██████  ██   ██ ███████ ██       ██████  ██   ████ ")
    console.log("")                                                                                                      
    console.log(`Juego ejecutandose en la URL: http://${localhost}:8080/`)
})
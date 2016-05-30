const net = require('net')
const fs = require('fs')

var server = net.createServer((socket) => {
	console.log('client connected')

	setTimeout(() => {
		socket.write('test hello\n')
		socket.end()
	}, 3000)

	socket.on('data', (buffer) => {
		console.log(buffer)
	})

	socket.on('end', () => {
		console.log('client disconnected')
	})

	let file_stream = fs.createWriteStream('socket.txt')
	socket.pipe(file_stream)
})

server.on('error', (err) => {
  throw err
})

server.listen(8888, '10.10.0.16', () => {
	address = server.address()
	console.log('server: %j', address)
})

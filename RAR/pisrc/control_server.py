import socket

HOST =''
PORT= 8888

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(1)
conn, addr = s.accept();

f = conn.makefile();


def moveForward(d):
  print "Moving forward : " + str(d)

def moveBackward(d):
  print "Moving backward : " + str(d) 

def turnLeft(d):
  print "Turning left : " + str(d) 

def turnRight(d):
  print "Turning right : " +str(d) 

options = {
  'L' : turnLeft,
  'R' : turnRight,
  'F' : moveForward,
  'B' : moveBackward
}

def process_line(line):
  command = line[0] 
  value = float(line[1:len(line)-1])
  options[command](value)

while True:
  process_line(f.readline());


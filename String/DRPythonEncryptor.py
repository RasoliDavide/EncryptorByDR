import hashlib
hasher = hashlib.sha512()

def generate_hash(valore):
    hasher = hashlib.sha512()
    hasher.update(valore.encode())
    return hasher.hexdigest()

def encrypt(valore, pw):
    byteVL = valore.encode('ascii')
    bytePW = generate_hash(pw).encode('ascii')
    print(byteVL, bytePW)
    crypted = []
    for i in range(len(valore)):
        print(byteVL[i], bytePW[i], byteVL[i] + bytePW[i])
        somma = byteVL[i] + bytePW[i]
        somma -= 128
        crypted.append(somma)
    print(crypted)
    strCrypted = str()
    for i in range(len(crypted)):
        print("Valori prima e dopo")
        print(bytes(crypted[i]).decode('ascii'), bytes(crypted[i]))
        strCrypted.join(bytes(crypted[i]).decode('ascii'))
    return strCrypted

print("Input the word you want to crypt")
parola = input()
print("Now input the password")
password = input()
print(len(encrypt(parola, password)))


#sha512 => Output 128 caratteri
"""
st = input()
sa = input()
stba = bytearray(st.encode())
saba = bytearray(sa.encode())

print(stba[0], saba[0], (stba[0] + saba[0]))
"""

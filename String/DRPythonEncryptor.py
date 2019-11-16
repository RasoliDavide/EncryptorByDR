import hashlib
hasher = hashlib.sha512()

def generate_hash(valore):
    hasher = hashlib.sha512()
    hasher.update(valore.encode())
    return hasher.hexdigest()

def encrypt(valore, pw):
    byteVL = bytearray(valore.encode())
    bytePW = bytearray(generate_hash(pw).encode())
    crypted = bytearray()
    for i in range(len(valore)):
        somma = byteVL[i] + bytePW[i]
        crypted.append(somma)
    return (bytes(crypted)).decode()


print("Input the word you want to crypt")
parola = input()
print("Now input the password")
password = input()
print(encrypt(parola, password))

#sha512 => Output 128 caratteri
"""
st = input()
sa = input()
stba = bytearray(st.encode())
saba = bytearray(sa.encode())

print(stba[0], saba[0], (stba[0] + saba[0]))
"""

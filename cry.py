import os
import hashlib
from Crypto import Random
from Crypto.Cipher import AES
from base64 import b64encode, b64decode

class AESCipher(object):
    def __init__(self, key):
        self.block_size = AES.block_size
        self.key = hashlib.sha256(key.encode()).digest()

    file_path = 'log_proc.txt'

    def read_text_file(self):
        # with open(self.file_path, 'r') as f:
        #         pt = f.read() 
        # return pt
        f = open(self.file_path, "r")
        pt = f.read()
        f.close()
        return pt

    def write_text_file(self,pt):
        # with open(self.file_path, 'a') as f:
        #         f.write(pt) #print(f.read())
        f = open(self.file_path, "a")
        f.write(pt)
        f.close()

    def write_new_text_file(self,pt):
        # with open(self.file_path, 'w') as f:
        #         f.write(pt)
        f = open(self.file_path,"w")
        f.write(pt)
        f.close()
    
    def encrypt(self, plain_text):
        plain_text = self.__pad(plain_text)
        iv = Random.new().read(self.block_size)
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        encrypted_text = cipher.encrypt(plain_text.encode())
        return b64encode(iv + encrypted_text).decode("utf-8")

    def decrypt(self, encrypted_text):
        encrypted_text = b64decode(encrypted_text)
        iv = encrypted_text[:self.block_size]
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        plain_text = cipher.decrypt(encrypted_text[self.block_size:]).decode("utf-8")
        return self.__unpad(plain_text)

    def __pad(self, plain_text):
        number_of_bytes_to_pad = self.block_size - len(plain_text) % self.block_size
        ascii_string = chr(number_of_bytes_to_pad)
        padding_str = number_of_bytes_to_pad * ascii_string
        padded_plain_text = plain_text + padding_str
        return padded_plain_text

    @staticmethod
    def __unpad(plain_text):
        last_character = plain_text[len(plain_text) - 1:]
        return plain_text[:-ord(last_character)]

password = input('Enter password:')
cipher_text = ""
plain_text = ""

# aes = AESCipher(password)
# pt = "hello ved"
# cipher_text = aes.encrypt(pt)
# print(cipher_text)
# dec = aes.decrypt(cipher_text)
# print(dec) 

# pt = aes.read_text_file()
# print(pt)
# cipher_text = aes.encrypt(pt)
# print(cipher_text)
# aes.write_new_text_file(cipher_text)

# password = input('Enter password:')
# cipher_text = ""
# plain_text = ""

aes = AESCipher(password)
sw = int(input('\nSelect \n1. READ DATA\n 2.ADD DATA\n\n'))


if sw == 1:
    cipher_text = aes.read_text_file()
    # print(cipher_text)
    plain_text = aes.decrypt(cipher_text)
    print(plain_text)
elif sw == 2:
    cipher_text = aes.read_text_file()
    # print(cipher_text)
    plain_text = aes.decrypt(cipher_text)
    #print(f"plain text :{plain_text}")
    new_data = input("Enter new data ")
    plain_text = plain_text +'\n' +new_data +'\n'
    print(plain_text)
    cipher_text = aes.encrypt(plain_text)
    # print(cipher_text)
    aes.write_new_text_file(cipher_text)
elif sw == 5:
    print("Initializing the file...")
    plain_text = aes.read_text_file()
    print(plain_text)
    cipher_text = aes.encrypt(plain_text)
    print(cipher_text)
    aes.write_new_text_file(cipher_text)
else :
    print('wrong value')
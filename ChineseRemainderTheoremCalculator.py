# x===3 (mod 5)
# x===1 (mod 7)
# x===6 (mod 8)

# ans%17 == 0
# (ans+2)%13 == 0
# (ans+3)%19 == 0
#
#ans = 0 (mod 17)
#ans = 11 (mod 13)
#ans = 16 (mod 19)

#ans = n (mod b)
ni =[17,13,19]
# N = the product of all n
N = 1
for n in ni:
    N*=n
bi = [0,11,16]
Ni = [N//n for n in ni]
xi = []
product = []
for i in range(len(Ni)):
    num = Ni[i]%ni[i]
    found = False
    x = 1
    while not found:
        if (x*num)%ni[i]==1:
            found = True
            break
        x+=1
    xi.append(x)
print(xi)
for i in range(len(bi)):
    product.append(bi[i]*Ni[i]*xi[i])

ans = sum(product)%N
finAns = ans
while not finAns%17==0:
    finAns+=N
print(finAns)
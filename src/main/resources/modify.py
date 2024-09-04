# f = open('locations.txt', 'r')
# content = f.readlines()

# res = ""
# i=0
# for line in content:
#     if(i<=3):
#         res+=line.replace("\n", "")
#     else:
#         res+=line
#         i=0
#     i+=1

# print(res)
f = open('comma-seperate.txt', 'r')
content = f.readlines()

res = ""
for line in content:
    line = line.replace("   ", "")
    line = line.replace(",,", ",")
    res+=line

print(res)
# f = open('check.txt', 'r')
# content = f.readlines()

# res = ""
# for line in content:
#     line = line.replace("(", "")
#     line = line.replace(")", "")
#     line = line.replace("\"", "")
#     res += line

# print(res)

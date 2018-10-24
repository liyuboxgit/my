#位置参数
def power(x, n):
    s = 1
    while n > 0:
        n = n - 1
        s = s * x
    return s

print(power(5,2))

#默认参数, 定义默认参数要牢记一点：默认参数必须指向不变对象！
def power1(x, n=2):
    s = 1
    while n > 0:
        n = n - 1
        s = s * x
    return s

print(power1(5,3))

#可变参数
def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum
nums = (1, 2, 3)
print(calc(nums[0],nums[2]))

#关键字参数
def person(name, age, **kw):
    print('name:', name, 'age:', age, 'other:', kw)

print(person('Adam', 45, gender='M', job='Engineer'))
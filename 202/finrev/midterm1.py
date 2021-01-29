def rev_lis(lis):
    print(lis)
    if len(lis) == 1:
        return([lis[0]])
    else:
        return([lis[len(lis) - 1]] + rev_lis(lis[:len(lis) - 1]))

print(rev_lis([1,2,3,4,5]))

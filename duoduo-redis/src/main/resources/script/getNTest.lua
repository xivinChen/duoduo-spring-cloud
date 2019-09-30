local result={}
for i=1,#(KEYS) do
    result[i]=redis.call('get',KEYS[i])
end
return result
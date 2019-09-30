local result={}
for i=1,#(KEYS) do
    result[i]=redis.call('set',KEYS[i],i)
end
return result
local result={}
local number=ARGV[1]
for i=1,#(KEYS) do
    result[i]=redis.call('ZINCRBY','userComboTime',number,KEYS[i])
end
return result
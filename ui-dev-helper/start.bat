echo '-------------------------start install'

echo '-------------------------npm i'
call npm i

echo '-------------------------bower i'
call bower i


echo '-------------------------open browser'
start http://localhost:8020/src/

echo '-------------------------gulp'
call gulp


echo '-------------------------finished install'



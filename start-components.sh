# OS specific support (must be 'true' or 'false').
pingcountflag="-c"
case "`uname`" in
  CYGWIN* )
    ;;
  Darwin* )
    ;;
  MINGW* )
    pingcountflag="-n"
    ;;
esac

# Add check to see if dcokerhost is defined. If not, then throw error

export DOCKERHOST=`ping $pingcountflag 1 dockerhost | grep -Eo -m 1 '[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}';`
echo "DOCKERHOST = $DOCKERHOST"

docker-compose -f docker-compose.yml pull
docker-compose -f docker-compose.yml up -d
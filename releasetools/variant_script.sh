#!/sbin/sh

set -e

# Helper functions
copy()
{
    if [ -e /sbin/toybox ]
    then
        /sbin/toybox cp --preserve=a "$1" "$2"
    else
        /sbin/busybox cp -c "$1" "$2"
    fi
}

# Detect variant and copy its specific-blobs
modelid=`getprop ro.boot.mid`

case $modelid in
    "2PZF10000") variant="uhl" ;;
    "2PZF30000") variant="dtwl" ;;
    *)           variant="dugl" ;;
esac

if [ "$variant" == "uhl" ] || [ "$variant" == "dtwl" ] || [ "$variant" == "dugl" ]; then
  basedir="/system/system/vendor/blobs/$variant/"
  if [ -d $basedir ]; then
    cd $basedir

    for file in `find . -type f` ; do
      mkdir -p `dirname /system/system/vendor/lib64/$file`
      copy $file /system/system/vendor/lib64/$file
    done
  else
    echo "Expected source directory does not exist!"
    exit 1
  fi
fi

exit 0

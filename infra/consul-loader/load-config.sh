#!/bin/sh

echo "Waiting for Consul..."

until curl -s http://consul:8500/v1/status/leader | grep -q ":"; do
  sleep 2
done

echo "Scanning /config directory..."

files=$(find /config -type f \( -name "*.yml" -o -name "*.yaml" \))

if [ -z "$files" ]; then
  echo "⚠️  No config files found in /config"
  exit 0
fi

for file in $files; do
  # путь без /config/
  relative=$(echo "$file" | sed 's|/config/||')

  # service name = первая папка
  service=$(echo "$relative" | cut -d'/' -f1)

  filename=$(basename "$file")
  name="${filename%.*}"

  # профиль
  if echo "$name" | grep -q "-"; then
    profile=$(echo "$name" | sed 's/.*-//')
    consul_key="${service},${profile}"
  else
    consul_key="$service"
  fi

  echo "Uploading $file -> config/$consul_key/data"

  curl -s -X PUT --data-binary @"$file" \
    http://consul:8500/v1/kv/config/$consul_key/data

done

echo "✅ All configs loaded."
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem
$zipPath = Join-Path $env:TEMP 'gradle-8.9.bin.zip'
$zip = [System.IO.Compression.ZipFile]::OpenRead($zipPath)
foreach ($entry in $zip.Entries) {
    if ($entry.Name -like '*wrapper*.jar') {
        Write-Host "Found wrapper jar: $($entry.FullName)"
    }
}
$zip.Dispose()

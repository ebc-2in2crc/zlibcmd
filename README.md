zlibcmd
=======

zlibcmd is row zlib compression commandline program.

## Description

The zlibcmd program reads from standard input and writes to standard output either compressing or uncompressing its input using raw zlib compression. It can be used to uncompress or compress data that is compressed with raw zlib compression. This program is provided primarily as a jig tool, though it could be used for other purposes.

This program should not be used as a general purpose compression tool. Use something like gzip instead.

## Usage

Compressing a file

```sh
$ echo -n 'zlib' > decompressed.txt
$ zlib --compress < decompressed.txt > compressed.txt
```

Decompressing a compressed file

```sh
$ zlib --decompress < compressed.txt
zlib
```

Compressing and base64 encoding

```sh
$ echo -n 'zlib' | zlib --compress | base64
eJyryslMAgAEZAGy
```

Base64 decoding and decompressing

```sh
$ echo -n 'eJyryslMAgAEZAGy' | base64 --decode | zlib --decompress
zlib
```

## Installation

Download from the following url.

- [https://github.com/ebc-2in2crc/zlibcmd/releases](https://github.com/ebc-2in2crc/zlibcmd/releases)

Or, you can use Homebrew (Only macOS).

```sh
$ brew tap ebc-2in2crc/zlibcmd
$ brew install zlibcmd
```

## Contribution

1. Fork this repository
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
4. Push to the branch (git push origin my-new-feature)
5. Create new Pull Request

## License

[MIT](https://github.com/ebc-2in2crc/zlibcmd/blob/master/LICENSE)

## Author

[ebc-2in2crc](https://github.com/ebc-2in2crc)

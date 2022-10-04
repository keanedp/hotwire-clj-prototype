const path = require('path');
const webpack = require('webpack');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const bundleFileName = 'bundle';
const dirName = 'resources/public/dist';
const publicPath = '/dist/';
module.exports = (env, argv) => {
    return {
        mode: argv.mode === "production" ? "production" : "development",
        entry: {
            index: ['./src/js/index.js', './src/sass/index.scss']
        },
        devtool: argv.mode === 'production' ? 'source-map' : 'eval-cheap-module-source-map',
        performance: {
            maxEntrypointSize: 512000,
            maxAssetSize: 512000
        },
        output: {
            filename: `[name].${bundleFileName}.js`,
            chunkFilename: '[name].bundle.js',
            publicPath: publicPath,
            path: path.resolve(__dirname, dirName),
        },
        module: {
            rules: [
                {
                    test: /\.(js)$/,
                    exclude: /node_modules/,
                    use: ['babel-loader']
                },
                {
                    test: /\.s[c|a]ss$/,
                    use:
                        [
                            MiniCssExtractPlugin.loader,
                            {
                                loader: 'css-loader',
                                options: {
                                    url: {
                                        filter: (url, resourcePath) => {
                                            return !url.includes('/images/', 0);
                                        },
                                    }
                                }
                            },
                            {
                                loader: 'postcss-loader',
                                options: {
                                    postcssOptions: {
                                        plugins: argv.mode === 'production'
                                            ? [require('autoprefixer')(), require('cssnano')()]
                                            : [require('autoprefixer')()]
                                    }
                                }
                            },
                            'sass-loader'
                        ]
                },
                {
                    test: /\.(svg|eot|woff|woff2|ttf)$/,
                    use: [
                        {
                            loader: 'file-loader',
                            options: {
                                name: '[name]-[hash:6].[ext]',
                                outputPath: 'fonts/'
                            }
                        }
                    ]
                }
            ]
        },
        resolve: {
            extensions: ['*', '.js']
        },
        plugins: [
            new CleanWebpackPlugin({
                cleanStaleWebpackAssets: false
            }),
            new MiniCssExtractPlugin({
                filename: '[name].' + bundleFileName + '.css'
            })
        ]
    };
};

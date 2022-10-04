import * as Turbo from "@hotwired/turbo"

document.addEventListener('DOMContentLoaded', () => {
    console.log('Init App...');
}, false);

document.documentElement.addEventListener('turbo:frame-render', (e) => {
    console.log('turbo:frame-render event called')
});

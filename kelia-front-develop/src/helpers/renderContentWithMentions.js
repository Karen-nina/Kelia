export function renderContentWithMentions(content, highlightTerm, mentionedData) {
  const mentionRegex = /\[\[(recipe|product|place):(.*?)\]\]/g;

  const PATH = {
    recipe: 'receta',
    product: 'productos',
    place: 'establecimiento',
  };

  content = content.replace(mentionRegex, (match, type, title) => {
    const arrayMap = {
      recipe: mentionedData.recipes || [],
      product: mentionedData.products || [],
      place: mentionedData.places || [],
    };

    const collection = arrayMap[type];
    const item = collection.find(
      (el) => el.title === title.trim() || el.name === title.trim()
    );

    if (item) {
      const path = `/${PATH[type]}/${item.id}`;
      return `<a href="${path}" class="mention-link" rel="noopener noreferrer">${title}</a>`;
    }

    return title;
  });

  if (highlightTerm) {
    const regex = new RegExp(`(${highlightTerm})`, 'gi');
    content = content.replace(regex, '<mark>$1</mark>');
  }

  return content;
}
export const timeAgo = (dateString) => {
  const now = new Date();
  const postDate = new Date(dateString);

  if (isNaN(postDate.getTime())) return 'Fecha inválida';

  const diffInSeconds = Math.floor((now - postDate) / 1000);
  const diffInMinutes = Math.floor(diffInSeconds / 60);
  const diffInHours = Math.floor(diffInSeconds / 3600);

  const formatTime = (date) => {
    return date.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' });
  };

  const isToday = now.toDateString() === postDate.toDateString();

  const yesterday = new Date();
  yesterday.setDate(now.getDate() - 1);
  const isYesterday = yesterday.toDateString() === postDate.toDateString();

  if (diffInSeconds < 60) {
    return `hace ${diffInSeconds} segundo${diffInSeconds !== 1 ? 's' : ''}`;
  } else if (diffInMinutes < 60) {
    return `hace ${diffInMinutes} minuto${diffInMinutes !== 1 ? 's' : ''}`;
  } else if (diffInHours < 3) {
    return `hace ${diffInHours} hora${diffInHours !== 1 ? 's' : ''}`;
  } else if (isToday) {
    return `hoy a las ${formatTime(postDate)}`;
  } else if (isYesterday) {
    return `ayer a las ${formatTime(postDate)}`;
  } else if (postDate.getFullYear() === now.getFullYear()) {
    return postDate.toLocaleDateString('es-ES', { day: 'numeric', month: 'short' });
  } else {
    return postDate.toLocaleDateString('es-ES', {
      day: 'numeric',
      month: 'short',
      year: 'numeric',
    });
  }
};
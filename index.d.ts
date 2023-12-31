export function initialise(
  workspaceId: string,
  token: string,
  userId: string,
  version: string
): void;

export function registerPush(
  xiaomiApplicationId: string,
  xiaomiApplicationKey: string,
  pushRegion: string,
  integrationId: string
): void;

export function registerFCM(integrationId: string): void;

export function identifyUser(uniqueID: string, userName: string): void;

export function updateStatus(callBackUrl: string, status: string): void;

export function resetUser(): void;

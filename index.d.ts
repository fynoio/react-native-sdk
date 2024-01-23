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
  integrationId: string,
  isApns: boolean = false
): void;

export function identifyUser(uniqueID: string, userName: string): void;

export function mergeProfile(
  oldDistinctId: string,
  newDistinctId: string
): void;

export function updateStatus(callBackUrl: string, status: string): void;

export function resetUser(): void;
